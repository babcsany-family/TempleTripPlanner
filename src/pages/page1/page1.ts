import {Component, OnInit} from '@angular/core';

import { NavController } from 'ionic-angular';
import {TranslateService} from "ng2-translate";
import {CreateTempleTripPage} from "../create-temple-trip/create-temple-trip";

@Component({
  selector: 'page-page1',
  templateUrl: 'page1.html'
})
export class Page1 implements OnInit {

  public i18n: {[x: string]: string} = {};
  public createPage = CreateTempleTripPage;

  constructor(
    public navCtrl: NavController,
    private translate: TranslateService
  ) {
  }

  ngOnInit() {
    console.log('Page1.ngOnInit() has been called');
    this.translate.setDefaultLang('en');
    this.translate.use(this.translate.getBrowserLang());
    this.translate.get(['app.title'])
        .subscribe(translations => this.i18n = translations);
  }

}
