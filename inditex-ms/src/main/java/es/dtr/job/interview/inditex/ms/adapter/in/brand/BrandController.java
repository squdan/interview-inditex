package es.dtr.job.interview.inditex.ms.adapter.in.brand;

import es.dtr.job.interview.inditex.ms.domain.service.brand.BrandService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RestController
@RequiredArgsConstructor
public class BrandController implements BrandControllerInterface {

    // Dependencies
    private final BrandService crudService;

}
