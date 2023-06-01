package ru.netology;

import com.codeborne.selenide.Condition;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DeliveryCardTest {
    private @NotNull String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldReturnSuccessIfFieldsAreFilledInCorrectly() {
        open("http://localhost:9999/");

        $("[data-test-id=city] input").setValue("Пермь");
        String currentDate = generateDate(4, "dd,MM,yyyy");
        $("[data-test-id=data] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[date-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Василий Вася");
        $("[data-test-id=phone] input").setValue("+79557385965");
        $("[data-test-id=agreement]").click();
        $("button__content").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронированa на " + currentDate));
    }
}