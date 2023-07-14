package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.CompanyDTO;
import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.dto.ProductDTO;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.enums.InvoiceStatus;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.InvoiceProductRepository;
import com.sc.accounting_smart_cookies.service.InvoiceProductService;
import com.sc.accounting_smart_cookies.service.InvoiceService;
import com.sc.accounting_smart_cookies.service.ProductService;
import com.sc.accounting_smart_cookies.service.SecurityService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;
    private final InvoiceService invoiceService;
    private final ProductService productService;
    private final SecurityService securityService;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil,
                                     @Lazy InvoiceService invoiceService, @Lazy ProductService productService, SecurityService securityService) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
        this.invoiceService = invoiceService;
        this.productService = productService;
        this.securityService = securityService;
    }

    @Override
    public List<InvoiceProductDTO> listAll() {
        return invoiceProductRepository.findAllByInvoiceCompanyTitle
                        (securityService.getLoggedInUser().getCompany().getTitle()).stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceProductDTO> findAllByInvoiceId(Long invoiceId) {

        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllByInvoice_Id(invoiceId);

        return invoiceProducts.stream().map(invoiceProduct ->
                        mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
                .peek(dto -> dto.setTotal(dto.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity() *
                        (dto.getTax() + 100) / 100d))))
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceProductDTO findById(Long id) {

        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id).orElseThrow();

        return mapperUtil.convert(invoiceProduct, new InvoiceProductDTO());
    }

    @Override
    public void save(InvoiceProductDTO invoiceProductDTO, Long invoiceId) {

        Invoice invoice = mapperUtil.convert(invoiceService.findById(invoiceId), new Invoice());
        InvoiceProduct invoiceProduct = mapperUtil.convert(invoiceProductDTO, new InvoiceProduct());

        invoiceProduct.setInvoice(invoice);
        invoiceProduct.setId(null);
        invoiceProduct.setProfitLoss(BigDecimal.ZERO);

        invoiceProductRepository.save(invoiceProduct);
    }

    @Override
    public void deleteById(Long id) {

        Optional<InvoiceProduct> invoiceProduct = invoiceProductRepository.findById(id);

        if (invoiceProduct.isPresent()) {
            invoiceProduct.get().setIsDeleted(true);
            invoiceProductRepository.save(invoiceProduct.get());
        }
    }

    @Override
    public void completeApproval(Long invoiceId, InvoiceType type) {

        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllByInvoice_Id(invoiceId);

        if (type.equals(InvoiceType.PURCHASE)) {

            for (InvoiceProduct each : invoiceProducts) {

                updateProductQuantity(each, type);

                each.setRemainingQuantity(each.getQuantity());

                invoiceProductRepository.save(each);
            }
        } else {
            for (InvoiceProduct each : invoiceProducts) {

                if (each.getProduct().getQuantityInStock() >= each.getQuantity()) {

                    updateProductQuantity(each, type);

                    each.setRemainingQuantity(each.getQuantity());

                    each.setProfitLoss(setProfitLossOfInvoiceProductsForSalesInvoice(each));

                    invoiceProductRepository.save(each);


                } else {
                    throw new RuntimeException("Insufficient quantity of product");
                }
            }
        }
    }

    @Override
    public boolean insufficientQuantity(InvoiceProductDTO invoiceProductDTO) {

        return invoiceProductDTO.getQuantity() > invoiceProductDTO.getProduct().getQuantityInStock();
    }

    private void updateProductQuantity(InvoiceProduct invoiceProduct, InvoiceType type) {

        ProductDTO productDTO = mapperUtil.convert(invoiceProduct.getProduct(), new ProductDTO());

        if (type.equals(InvoiceType.PURCHASE)) {
            productDTO.setQuantityInStock(productDTO.getQuantityInStock() + invoiceProduct.getQuantity());
        } else {
            productDTO.setQuantityInStock(productDTO.getQuantityInStock() - invoiceProduct.getQuantity());
        }
        productService.updateQuantity(productDTO);
    }

    @Override
    public BigDecimal setProfitLossOfInvoiceProductsForSalesInvoice(InvoiceProduct toBeSoldProduct) {

        List<InvoiceProduct> purchasedProducts = invoiceProductRepository
                .findInvoiceProductsByInvoiceInvoiceTypeAndProductAndRemainingQuantityNotOrderByIdAsc(
                        InvoiceType.PURCHASE, toBeSoldProduct.getProduct(), 0);

        BigDecimal profitLoss;

        for (InvoiceProduct purchasedProduct : purchasedProducts) {

            if (toBeSoldProduct.getRemainingQuantity() <= purchasedProduct.getRemainingQuantity()) {


                BigDecimal costTotalForQty = purchasedProduct.getPrice().multiply(
                        BigDecimal.valueOf(toBeSoldProduct.getRemainingQuantity() * (purchasedProduct.getTax() + 100) / 100d));
                BigDecimal salesTotalForQty = toBeSoldProduct.getPrice().multiply(
                        BigDecimal.valueOf(toBeSoldProduct.getRemainingQuantity() * (toBeSoldProduct.getTax() + 100) / 100d));
                profitLoss = salesTotalForQty.subtract(costTotalForQty);

                purchasedProduct.setRemainingQuantity(purchasedProduct.getRemainingQuantity() - toBeSoldProduct.getRemainingQuantity());
                toBeSoldProduct.setProfitLoss(toBeSoldProduct.getProfitLoss().add(profitLoss));
                toBeSoldProduct.setRemainingQuantity(0);
                invoiceProductRepository.save(purchasedProduct);
                invoiceProductRepository.save(toBeSoldProduct);
                break;
            } else {

                BigDecimal costTotalForQty = purchasedProduct.getPrice().multiply(
                        BigDecimal.valueOf(purchasedProduct.getRemainingQuantity() * (purchasedProduct.getTax() + 100) / 100d));
                BigDecimal salesTotalForQty = toBeSoldProduct.getPrice().multiply(
                        BigDecimal.valueOf(purchasedProduct.getRemainingQuantity() * (toBeSoldProduct.getTax() + 100) / 100d));
                profitLoss = salesTotalForQty.subtract(costTotalForQty);
                toBeSoldProduct.setRemainingQuantity(toBeSoldProduct.getRemainingQuantity() - purchasedProduct.getRemainingQuantity());
                purchasedProduct.setRemainingQuantity(0);
                toBeSoldProduct.setProfitLoss(profitLoss);
                invoiceProductRepository.save(purchasedProduct);
                invoiceProductRepository.save(toBeSoldProduct);

            }
        }
        return toBeSoldProduct.getProfitLoss();
    }

    @Override
    public List<InvoiceProductDTO> getAllProductWithStatusTypeAndCompanyTitle
            (InvoiceStatus status, InvoiceType type, String title) {

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoiceInvoiceStatusAndInvoiceInvoiceTypeAndInvoiceCompanyTitle(
                InvoiceStatus.APPROVED, InvoiceType.SALES, securityService.getLoggedInUser()
                        .getCompany().getTitle());

        return invoiceProductList.stream().map(invoiceProduct ->
                mapperUtil.convert(invoiceProduct, new InvoiceProductDTO())).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceProductDTO> findAllInvoicesByStatusApproved(InvoiceStatus status, String company) {
        List<InvoiceProduct> invoiceProductList =
                invoiceProductRepository.findAllByInvoiceInvoiceStatusAndInvoiceCompanyTitleOrderByInvoiceLastUpdateDateTimeDesc
                        (InvoiceStatus.APPROVED, securityService.getLoggedInUser().getCompany().getTitle());
        return invoiceProductList.stream().map(invoiceProduct ->
                mapperUtil.convert(invoiceProduct, new InvoiceProductDTO())).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceProductDTO> findAllByInvoiceStatusAndInvoiceTypeAndCompany(InvoiceStatus status, InvoiceType type) {

        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());

        return invoiceProductRepository.findAllByInvoiceInvoiceStatusAndInvoiceInvoiceTypeAndInvoiceCompany(status, type, company).stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceProductDTO> findAllInvoiceProductsByProductId(Long id) {
        return invoiceProductRepository.findAllInvoiceProductByProductId(id).stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
                .collect(Collectors.toList());
    }


}
