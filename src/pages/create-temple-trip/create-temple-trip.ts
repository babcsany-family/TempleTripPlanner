import { Component, ViewChild } from "@angular/core";
import { NavController, NavParams } from "ionic-angular";
import { TempleTripFormComponent } from "../../components/temple-trip-form/temple-trip-form";

/*
  Generated class for the CreateTempleTrip page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'page-create-temple-trip',
  templateUrl: 'create-temple-trip.html'
})
export class CreateTempleTripPage {

  @ViewChild('templeTrip') templeTrip: TempleTripFormComponent;

  constructor(public navCtrl: NavController, public navParams: NavParams) {}

  ionViewDidLoad() {
    console.log('ionViewDidLoad CreateTempleTripPage');
  }

  submitTrip() {
    this.templeTrip.submitTrip();
  }
}
