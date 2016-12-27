import {NgModule, ErrorHandler} from '@angular/core';
import {IonicApp, IonicModule, IonicErrorHandler} from 'ionic-angular';
import {TranslateModule} from 'ng2-translate/ng2-translate';
import {Http} from "@angular/http";
import {TranslateStaticLoader, TranslateLoader} from "ng2-translate";
import {IonCalendarModule} from "@ionic2-extra/calendar";

import {MyApp} from './app.component';
import {Page1} from '../pages/page1/page1';
import {Page2} from '../pages/page2/page2';
import {CreateTempleTripPage} from "../pages/create-temple-trip/create-temple-trip";
import {TempleTripFormComponent} from "../components/temple-trip-form/temple-trip-form";
import {DatePicker} from "ionic2-date-picker/ionic2-date-picker";

export function createTranslateLoader(http: Http) {
  return new TranslateStaticLoader(http, 'assets/i18n', '.json');
}

@NgModule({
  declarations: [
    MyApp,
    Page1,
    Page2,
    CreateTempleTripPage,
    TempleTripFormComponent,
    DatePicker
  ],
  imports: [
    IonicModule.forRoot(MyApp),
    TranslateModule.forRoot({
      provide: TranslateLoader,
      useFactory: (createTranslateLoader),
      deps: [Http]
    }),
    IonCalendarModule.forRoot()
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    Page1,
    Page2,
    CreateTempleTripPage,
    TempleTripFormComponent,
    DatePicker
  ],
  providers: [{provide: ErrorHandler, useClass: IonicErrorHandler}]
})
export class AppModule {
}
