package br.com.erudio.controller;

import br.com.erudio.dto.ExchangeDto;
import br.com.erudio.environment.InstanceInformationService;
import br.com.erudio.model.Book;
import br.com.erudio.proxy.ExchangeProxy;
import br.com.erudio.repository.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book Endpoint")
@RestController
@RequestMapping("book-service")
public class BookController {

    private final InstanceInformationService informationService;

    private final BookRepository repository;

    private final ExchangeProxy proxy;

    public BookController(InstanceInformationService informationService, BookRepository repository, ExchangeProxy proxy) {
        this.informationService = informationService;
        this.repository = repository;
        this.proxy = proxy;
    }

    @Operation(summary = "Find a specific book by your ID")
    @GetMapping(value = "/{id}/{currency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book findBook(
            @PathVariable Long id,
            @PathVariable String currency
    ) {
        String port = informationService.retrieveServerPort();

        var book = repository.findById(id).orElseThrow();

        ExchangeDto exchangeDto = proxy.getExchange(book.getPrice(), "USD", currency);

        book.setEnvironment("BOOK PORT: " + port + " EXCHANGE PORT: " + exchangeDto.getEnvironment());
        book.setPrice(exchangeDto.getConvertedValue());
        book.setCurrency(currency);
        return book;
    }
}
