import { NgModule, ErrorHandler } from '@angular/core';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { TranslateModule } from 'ng2-translate/ng2-translate';
import { Http } from "@angular/http";
import { TranslateStaticLoader, TranslateLoader } from "ng2-translate";
import { IonCalendarModule } from "@ionic2-extra/calendar";

import { MyApp } from './app.component';
import { CreateTempleTripPage } from "../pages/create-temple-trip/create-temple-trip";
import { TempleTripFormComponent } from "../components/temple-trip-form/temple-trip-form";
import { ReactiveFormsModule } from "@angular/forms";
import { DataStorage } from "../providers/data-storage";

export function createTranslateLoader(http: Http) {
  return new TranslateStaticLoader(http, 'assets/i18n', '.json');
}

@NgModule({
  declarations: [
    MyApp,
    CreateTempleTripPage,
    TempleTripFormComponent
  ],
  imports: [
    IonicModule.forRoot(MyApp),
    TranslateModule.forRoot({
      provide: TranslateLoader,
      useFactory: (createTranslateLoader),
      deps: [Http]
    }),
    IonCalendarModule.forRoot(),
    ReactiveFormsModule
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    CreateTempleTripPage,
    TempleTripFormComponent
  ],
  providers: [
    {provide: ErrorHandler, useClass: IonicErrorHandler},
    DataStorage
  ]
})
export class AppModule {
}
