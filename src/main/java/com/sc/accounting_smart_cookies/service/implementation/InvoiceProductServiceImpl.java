package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.dto.ProductDTO;
import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.InvoiceProductRepository;
import com.sc.accounting_smart_cookies.service.InvoiceProductService;
import com.sc.accounting_smart_cookies.service.InvoiceService;
import com.sc.accounting_smart_cookies.service.ProductService;
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

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil,
                                     @Lazy InvoiceService invoiceService, @Lazy ProductService productService) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
        this.invoiceService = invoiceService;
        this.productService = productService;
    }

    @Override
    public List<InvoiceProductDTO> findAllByInvoiceId(Long invoiceId) {

        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllByInvoice_Id(invoiceId);

        return invoiceProducts.stream().map(invoiceProduct ->
                        mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
                .peek(dto -> dto.setTotal(dto.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity() *
                        (dto.getTax()+100)/100d))))
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
        productService.update(productDTO.getId(), productDTO);
    }
}
