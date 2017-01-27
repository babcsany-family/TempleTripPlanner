import { Component, ViewChild } from '@angular/core';
import { Nav, Platform } from 'ionic-angular';
import { StatusBar, Splashscreen } from 'ionic-native';

import { Page1 } from '../pages/page1/page1';
import { Page2 } from '../pages/page2/page2';
import { TranslateService } from "ng2-translate";
import { CreateTempleTripPage } from "../pages/create-temple-trip/create-temple-trip";
import { DataStorage } from "../providers/data-storage";


@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  @ViewChild(Nav) nav: Nav;

  rootPage: any = CreateTempleTripPage;

  pages: Array<{ title: string, component: any }>;

  constructor(public platform: Platform,
              private translate: TranslateService,
              private db: DataStorage) {
    this.initializeApp();

    // used for an example of ngFor and navigation
    this.pages = [
      {title: 'Shared by Others', component: Page1},
      {title: 'My Temple Trips', component: Page2}
    ];

  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.translate.setDefaultLang('en');
      this.translate.use(this.translate.getBrowserLang());
      this.db.openDb().then(() => {
        // this.db.init();
        console.log('Database has been opened!');
        StatusBar.styleDefault();
        Splashscreen.hide();
      }).catch(error => {
        console.log('Error happened: ' + JSON.stringify(error));
      });
    });
  }

  openPage(page) {
    // Reset the content nav to have just this page
    // we wouldn't want the back button to show in this scenario
    this.nav.setRoot(page.component);
  }
}
