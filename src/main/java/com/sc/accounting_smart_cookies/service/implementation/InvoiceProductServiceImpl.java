package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.InvoiceProductRepository;
import com.sc.accounting_smart_cookies.service.InvoiceProductService;
import com.sc.accounting_smart_cookies.service.InvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;
    private final InvoiceService invoiceService;

    @Override
    public List<InvoiceProductDTO> findAllByInvoiceId(Long invoiceId) {

        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllByInvoice_Id(invoiceId);

        return invoiceProducts.stream().map(invoiceProduct ->
                        mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
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
//        invoiceProduct.setId(null);
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
}
