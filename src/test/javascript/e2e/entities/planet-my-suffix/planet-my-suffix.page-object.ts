import { element, by, promise, ElementFinder } from 'protractor';

export class PlanetComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-planet-my-suffix div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PlanetUpdatePage {
    pageTitle = element(by.id('jhi-planet-my-suffix-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    planetNameInput = element(by.id('field_planetName'));
    planetVelocityInput = element(by.id('field_planetVelocity'));
    planetRadiusInput = element(by.id('field_planetRadius'));
    solarSystemSelect = element(by.id('field_solarSystem'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setPlanetNameInput(planetName): promise.Promise<void> {
        return this.planetNameInput.sendKeys(planetName);
    }

    getPlanetNameInput() {
        return this.planetNameInput.getAttribute('value');
    }

    setPlanetVelocityInput(planetVelocity): promise.Promise<void> {
        return this.planetVelocityInput.sendKeys(planetVelocity);
    }

    getPlanetVelocityInput() {
        return this.planetVelocityInput.getAttribute('value');
    }

    setPlanetRadiusInput(planetRadius): promise.Promise<void> {
        return this.planetRadiusInput.sendKeys(planetRadius);
    }

    getPlanetRadiusInput() {
        return this.planetRadiusInput.getAttribute('value');
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
