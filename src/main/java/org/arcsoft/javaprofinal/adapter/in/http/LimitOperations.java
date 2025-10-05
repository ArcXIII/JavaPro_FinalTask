package org.arcsoft.javaprofinal.adapter.in.http;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.arcsoft.javaprofinal.adapter.in.http.dto.CurrentLimitDto;
import org.arcsoft.javaprofinal.adapter.in.http.dto.LimitUpdateRequest;
import org.arcsoft.javaprofinal.adapter.in.http.dto.MaxLimitDto;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Операции с лимитом пользователя")
@RequestMapping("limits/{userId}")
public interface LimitOperations {

    @Operation(summary = "Получить максимальный лимит пользователя на сумму в день",
            description = "Возвращает максимальный лимит для пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Лимит успешно получен")
            })
    @GetMapping
    MaxLimitDto getMaxLimit(@PathVariable("userId") Long userId);

    @Operation(summary = "Получить текущий лимит пользователя на сумму в день",
            description = "Возвращает текущий лимит для пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Лимит успешно получен")
            })
    @GetMapping("current")
    MaxLimitDto getCurrentLimit(@PathVariable("userId") Long userId);

    @Operation(summary = "Обновить максимальный лимит пользователя на сумму в день",
            description = "Возвращает обновлённый лимит пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Лимит успешно обновлён")
            })
    @PutMapping
    MaxLimitDto updateMaxLimit(@PathVariable("userId") Long userId, @RequestBody LimitUpdateRequest updateRequest);

    @Operation(summary = "Уменьшить текущую сумму лимита",
            description = "Возвращает информацию о текущем состоянии лимита",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Получено значение лимита"),
                    @ApiResponse(responseCode = "400", description = "Не удалось списать сумму по лимиту")
            })
    @PostMapping
    CurrentLimitDto decreaseCurrentLimit(@PathVariable("userId") Long userId, @RequestBody LimitUpdateRequest updateRequest);
}
