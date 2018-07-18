import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { PlanetComponentsPage, PlanetUpdatePage } from './planet-my-suffix.page-object';

describe('Planet e2e test', () => {
    let navBarPage: NavBarPage;
    let planetUpdatePage: PlanetUpdatePage;
    let planetComponentsPage: PlanetComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Planets', () => {
        navBarPage.goToEntity('planet-my-suffix');
        planetComponentsPage = new PlanetComponentsPage();
        expect(planetComponentsPage.getTitle()).toMatch(/solarSystemApp.planet.home.title/);
    });

    it('should load create Planet page', () => {
        planetComponentsPage.clickOnCreateButton();
        planetUpdatePage = new PlanetUpdatePage();
        expect(planetUpdatePage.getPageTitle()).toMatch(/solarSystemApp.planet.home.createOrEditLabel/);
        planetUpdatePage.cancel();
    });

    it('should create and save Planets', () => {
        planetComponentsPage.clickOnCreateButton();
        planetUpdatePage.setPlanetNameInput('planetName');
        expect(planetUpdatePage.getPlanetNameInput()).toMatch('planetName');
        planetUpdatePage.setPlanetVelocityInput('5');
        expect(planetUpdatePage.getPlanetVelocityInput()).toMatch('5');
        planetUpdatePage.setPlanetRadiusInput('5');
        expect(planetUpdatePage.getPlanetRadiusInput()).toMatch('5');
        planetUpdatePage.solarSystemSelectLastOption();
        planetUpdatePage.save();
        expect(planetUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
