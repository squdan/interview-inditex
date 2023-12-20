package es.dtr.job.interview.inditex.ms.application.rest.price;

import es.dtr.job.interview.commons.hexagonal.application.rest.ApiConfiguration;
import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudController;
import es.dtr.job.interview.inditex.ms.domain.entity.PriceEntity;
import es.dtr.job.interview.inditex.ms.infrastructure.configuration.OpenApiConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.List;

@Validated
@Tag(name = PriceControllerInterface.API_RESOURCE)
@RequestMapping(PriceControllerInterface.BASE_PATH)
public interface PriceControllerInterface extends CrudController<PriceDto, PriceEntity, Long> {

    // Paths
    String API_RESOURCE = "prices";
    String BASE_PATH = OpenApiConfiguration.API_PATH + "/" + API_RESOURCE;
    String ENDPOINT_GET_PRICE_BY = "/products/{productId}/brands/{brandId}/{date}";

    default Link[] getHateoas(final Long id) {
        List<Link> result = List.of(
                getHateoasGet(id),
                getHateoasFind(),
                WebMvcLinkBuilder
                        .linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(PriceControllerInterface.class)
                                        .getWithProductBrandDate(null, null, null))
                        .withRel("get-by-product-brand-date"),
                getHateoasCreate(),
                getHateoasUpdate(id),
                getHateoasDelete(id)
        );
        return result.toArray(new Link[]{});
    }

    @Operation(summary = "Get price from product at selected time.", security = @SecurityRequirement(name = ApiConfiguration.API_SECURITY_NAME))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "403", description = "Authentication required.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Element not found.", content = @Content)
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = PriceControllerInterface.ENDPOINT_GET_PRICE_BY, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<PriceDto>> getWithProductBrandDate(
            @NotNull @PathVariable @Schema(description = "Product ID.", defaultValue = "35455") Long productId,
            @NotNull @PathVariable @Schema(description = "Brand ID.", defaultValue = "1") Long brandId,
            @NotNull @PathVariable @Schema(description = "Date to search.", defaultValue = "2020-12-14T10:00:00.000Z")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant date
    );
}
