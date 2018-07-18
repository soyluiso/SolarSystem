import { element, by, promise, ElementFinder } from 'protractor';

export class SolarSystemComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-solar-system-my-suffix div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SolarSystemUpdatePage {
    pageTitle = element(by.id('jhi-solar-system-my-suffix-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    systemNameInput = element(by.id('field_systemName'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setSystemNameInput(systemName): promise.Promise<void> {
        return this.systemNameInput.sendKeys(systemName);
    }

    getSystemNameInput() {
        return this.systemNameInput.getAttribute('value');
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
