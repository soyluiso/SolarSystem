import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { SolarSystemForecastComponentsPage, SolarSystemForecastUpdatePage } from './solar-system-forecast-my-suffix.page-object';

describe('SolarSystemForecast e2e test', () => {
    let navBarPage: NavBarPage;
    let solarSystemForecastUpdatePage: SolarSystemForecastUpdatePage;
    let solarSystemForecastComponentsPage: SolarSystemForecastComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SolarSystemForecasts', () => {
        navBarPage.goToEntity('solar-system-forecast-my-suffix');
        solarSystemForecastComponentsPage = new SolarSystemForecastComponentsPage();
        expect(solarSystemForecastComponentsPage.getTitle()).toMatch(/solarSystemApp.solarSystemForecast.home.title/);
    });

    it('should load create SolarSystemForecast page', () => {
        solarSystemForecastComponentsPage.clickOnCreateButton();
        solarSystemForecastUpdatePage = new SolarSystemForecastUpdatePage();
        expect(solarSystemForecastUpdatePage.getPageTitle()).toMatch(/solarSystemApp.solarSystemForecast.home.createOrEditLabel/);
        solarSystemForecastUpdatePage.cancel();
    });

    it('should create and save SolarSystemForecasts', () => {
        solarSystemForecastComponentsPage.clickOnCreateButton();
        solarSystemForecastUpdatePage.setDayInput('5');
        expect(solarSystemForecastUpdatePage.getDayInput()).toMatch('5');
        solarSystemForecastUpdatePage.setForecastInput('forecast');
        expect(solarSystemForecastUpdatePage.getForecastInput()).toMatch('forecast');
        solarSystemForecastUpdatePage.solarSystemSelectLastOption();
        solarSystemForecastUpdatePage.save();
        expect(solarSystemForecastUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
