import { element, by, promise, ElementFinder } from 'protractor';

export class SolarSystemForecastComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-solar-system-forecast-my-suffix div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SolarSystemForecastUpdatePage {
    pageTitle = element(by.id('jhi-solar-system-forecast-my-suffix-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dayInput = element(by.id('field_day'));
    forecastInput = element(by.id('field_forecast'));
    solarSystemSelect = element(by.id('field_solarSystem'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setDayInput(day): promise.Promise<void> {
        return this.dayInput.sendKeys(day);
    }

    getDayInput() {
        return this.dayInput.getAttribute('value');
    }

    setForecastInput(forecast): promise.Promise<void> {
        return this.forecastInput.sendKeys(forecast);
    }

    getForecastInput() {
        return this.forecastInput.getAttribute('value');
    }

    solarSystemSelectLastOption(): promise.Promise<void> {
        return this.solarSystemSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    solarSystemSelectOption(option): promise.Promise<void> {
        return this.solarSystemSelect.sendKeys(option);
    }

    getSolarSystemSelect(): ElementFinder {
        return this.solarSystemSelect;
    }

    getSolarSystemSelectedOption() {
        return this.solarSystemSelect.element(by.css('option:checked')).getText();
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
