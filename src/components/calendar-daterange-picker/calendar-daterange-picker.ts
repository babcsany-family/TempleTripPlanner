import { Component } from '@angular/core';

/*
  Generated class for the CalendarDaterangePicker component.

  See https://angular.io/docs/ts/latest/api/core/index/ComponentMetadata-class.html
  for more info on Angular 2 Components.
*/
@Component({
  selector: 'calendar-daterange-picker',
  templateUrl: 'calendar-daterange-picker.html'
})
export class CalendarDaterangePickerComponent {

  text: string;

  constructor() {
    console.log('Hello CalendarDaterangePicker Component');
    this.text = 'Hello World';
  }

}
