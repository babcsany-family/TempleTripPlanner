import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators, FormControl} from "@angular/forms";
import {DatePicker} from "ionic2-date-picker/ionic2-date-picker";
import {ToastController} from "ionic-angular";

/*
  Generated class for the TempleTripForm component.

  See https://angular.io/docs/ts/latest/api/core/index/ComponentMetadata-class.html
  for more info on Angular 2 Components.
*/
@Component({
  selector: 'temple-trip-form',
  templateUrl: 'temple-trip-form.html',
  providers: [DatePicker]
})
export class TempleTripFormComponent implements OnInit{
  ngOnInit(): void {
    console.log('SOMETHING from ngOnInit');
  }

  templeTripGroup: FormGroup;

  constructor(private formBuilder: FormBuilder, private datePicker: DatePicker, private toastCtrl: ToastController) {
    this.datePicker.onDateSelected.subscribe((date: Date) => toastCtrl.create({
      message: date.toISOString(),
      duration: 3000
    }).present());
    this.templeTripGroup = formBuilder.group({
      arrivalDate: ['', [Validators.required]],
      leaveDate: ['', Validators.required],
      datePeriod: [new Date(), []]
    });
  }

  ionViewDidLoad() {
    console.log('SOMETHING from ionViewDidLoad')
  }

  pickDate() {
    this.datePicker.showCalendar();
  }

  submitTrip() {

  }
}
