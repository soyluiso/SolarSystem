import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { SolarSystemComponentsPage, SolarSystemUpdatePage } from './solar-system-my-suffix.page-object';

describe('SolarSystem e2e test', () => {
    let navBarPage: NavBarPage;
    let solarSystemUpdatePage: SolarSystemUpdatePage;
    let solarSystemComponentsPage: SolarSystemComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SolarSystems', () => {
        navBarPage.goToEntity('solar-system-my-suffix');
        solarSystemComponentsPage = new SolarSystemComponentsPage();
        expect(solarSystemComponentsPage.getTitle()).toMatch(/solarSystemApp.solarSystem.home.title/);
    });

    it('should load create SolarSystem page', () => {
        solarSystemComponentsPage.clickOnCreateButton();
        solarSystemUpdatePage = new SolarSystemUpdatePage();
        expect(solarSystemUpdatePage.getPageTitle()).toMatch(/solarSystemApp.solarSystem.home.createOrEditLabel/);
        solarSystemUpdatePage.cancel();
    });

    it('should create and save SolarSystems', () => {
        solarSystemComponentsPage.clickOnCreateButton();
        solarSystemUpdatePage.setSystemNameInput('systemName');
        expect(solarSystemUpdatePage.getSystemNameInput()).toMatch('systemName');
        solarSystemUpdatePage.save();
        expect(solarSystemUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
