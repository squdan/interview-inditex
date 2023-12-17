package es.dtr.job.interview.inditex.ms;

import es.dtr.job.interview.commons.data.Currencies;
import es.dtr.job.interview.commons.util.DateTimeUtils;
import es.dtr.job.interview.inditex.ms.domain.service.brand.BrandDto;
import es.dtr.job.interview.inditex.ms.domain.service.price.PriceDto;
import es.dtr.job.interview.inditex.ms.domain.service.product.ProductDto;

import java.math.BigDecimal;

public interface TestDataIT {

    // Authentication Data
    String USERNAME_ADMIN = "admin";
    String PASSWORD_OK = "test";
    String USERNAME_NOT_EXISTS = "unknownUsername";
    String PASSWORD_KO = "wrongPassword";

    interface Prices {
        PriceDto PRICE_1 = PriceDto.builder()
                .priceList(1L)
                .brand(BrandDto.builder().id(1L).build())
                .product(ProductDto.builder().id(35455L).build())
                .startDate(DateTimeUtils.StringDateUtils.toInstantUtc("2020-06-14T00:00:00Z"))
                .endDate(DateTimeUtils.StringDateUtils.toInstantUtc("2020-12-31T23:59:59Z"))
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency(Currencies.EUR)
                .build();

        PriceDto PRICE_2 = PriceDto.builder()
                .priceList(2L)
                .brand(BrandDto.builder().id(1L).build())
                .product(ProductDto.builder().id(35455L).build())
                .startDate(DateTimeUtils.StringDateUtils.toInstantUtc("2020-06-14T15:00:00Z"))
                .endDate(DateTimeUtils.StringDateUtils.toInstantUtc("2020-06-14T18:30:00Z"))
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency(Currencies.EUR)
                .build();

        PriceDto PRICE_3 = PriceDto.builder()
                .priceList(3L)
                .brand(BrandDto.builder().id(1L).build())
                .product(ProductDto.builder().id(35455L).build())
                .startDate(DateTimeUtils.StringDateUtils.toInstantUtc("2020-06-15T00:00:00Z"))
                .endDate(DateTimeUtils.StringDateUtils.toInstantUtc("2020-06-15T11:00:00Z"))
                .priority(1)
                .price(new BigDecimal("30.50"))
                .currency(Currencies.EUR)
                .build();

        PriceDto PRICE_4 = PriceDto.builder()
                .priceList(4L)
                .brand(BrandDto.builder().id(1L).build())
                .product(ProductDto.builder().id(35455L).build())
                .startDate(DateTimeUtils.StringDateUtils.toInstantUtc("2020-06-15T16:00:00Z"))
                .endDate(DateTimeUtils.StringDateUtils.toInstantUtc("2020-12-31T23:59:59Z"))
                .priority(1)
                .price(new BigDecimal("38.95"))
                .currency(Currencies.EUR)
                .build();
    }
}
