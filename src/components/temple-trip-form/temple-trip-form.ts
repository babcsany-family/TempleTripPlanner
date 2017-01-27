import { Component, OnInit, ViewChild } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { ToastController } from "ionic-angular";
import { SocialSharing } from "ionic-native";
import { DataStorage } from "../../providers/data-storage";

/*
  Generated class for the TempleTripForm component.

  See https://angular.io/docs/ts/latest/api/core/index/ComponentMetadata-class.html
  for more info on Angular 2 Components.
*/
@Component({
  selector: 'temple-trip-form',
  templateUrl: 'temple-trip-form.html'
})
export class TempleTripFormComponent implements OnInit{
  ngOnInit(): void {
    console.log('SOMETHING from ngOnInit');
  }

  templeTripGroup: FormGroup;
  @ViewChild('arrivalDate') arrivalDate;
  @ViewChild('leaveDate') leaveDate;

  patrons: any[] = [
    {name: 'Peter Babcsany', gender: 'M', patronKindForHostel: 'Adult', picture: 'https://lh3.googleusercontent.com/8KNfVoakutrr6PlYWwRjJ53AYK42UBJgGezQG-I5lCyjovluq16B4qpqf0bI2hxxYycwPjgme6QxORuspS_IHRjoBbychGZBU93uB4bbVkjfgarXXdV9E1lDfcSyBYhTz9KXWQoHWTT_b3HyP0gtgszyEi1h4JgzL64lSzBiU5OgpgH0iF4KQDo_9vBQCOcFWYMiuUZLZqqmfpI5Y4orQIZQozOnppVGzGrfaQ1txRqJ3gXApoD2icAWpsuFf1VL9sgdqCyjLfIKPHDad_Nq3qCQ-b6PFmoQ8NnQoMn4S_tjCi6Cgy6p79TWdVJ-eOEyIw2XqFTU4D_khooo5FtbcOgwPs1es1e82tSDmUSzBEWmbWyXwPD5o0eMRrjqSM7LNPJBHu7s-gX1Omx4Fmr2pGGvPXg9t-UsG0R0ufwfcomflfSZ6-eJTFtlVmv3miygLV94wYh9PSzKKk7Rfa0qILT3pEZg5V5lkTQt07AowSD2XmITdMFEpVRpTNgcFHgJFZaYKeJ4FPg0MSEq0IbqGhUaXiuCqlltaQKNIqcTcrZ628anD7eiz4PpEqjiYArXm_7E4hJ1MrD4basHPS2U1AVJ6whmCz7izj0JYtI66Wpqoj7UJ7XB=s500-no'},
    {name: 'Peter Babcsany', gender: 'M', patronKindForHostel: 'Adult', picture: 'https://lh3.googleusercontent.com/8KNfVoakutrr6PlYWwRjJ53AYK42UBJgGezQG-I5lCyjovluq16B4qpqf0bI2hxxYycwPjgme6QxORuspS_IHRjoBbychGZBU93uB4bbVkjfgarXXdV9E1lDfcSyBYhTz9KXWQoHWTT_b3HyP0gtgszyEi1h4JgzL64lSzBiU5OgpgH0iF4KQDo_9vBQCOcFWYMiuUZLZqqmfpI5Y4orQIZQozOnppVGzGrfaQ1txRqJ3gXApoD2icAWpsuFf1VL9sgdqCyjLfIKPHDad_Nq3qCQ-b6PFmoQ8NnQoMn4S_tjCi6Cgy6p79TWdVJ-eOEyIw2XqFTU4D_khooo5FtbcOgwPs1es1e82tSDmUSzBEWmbWyXwPD5o0eMRrjqSM7LNPJBHu7s-gX1Omx4Fmr2pGGvPXg9t-UsG0R0ufwfcomflfSZ6-eJTFtlVmv3miygLV94wYh9PSzKKk7Rfa0qILT3pEZg5V5lkTQt07AowSD2XmITdMFEpVRpTNgcFHgJFZaYKeJ4FPg0MSEq0IbqGhUaXiuCqlltaQKNIqcTcrZ628anD7eiz4PpEqjiYArXm_7E4hJ1MrD4basHPS2U1AVJ6whmCz7izj0JYtI66Wpqoj7UJ7XB=s500-no'},
    {name: 'Peter Babcsany', gender: 'M', patronKindForHostel: 'Adult', picture: 'https://lh3.googleusercontent.com/8KNfVoakutrr6PlYWwRjJ53AYK42UBJgGezQG-I5lCyjovluq16B4qpqf0bI2hxxYycwPjgme6QxORuspS_IHRjoBbychGZBU93uB4bbVkjfgarXXdV9E1lDfcSyBYhTz9KXWQoHWTT_b3HyP0gtgszyEi1h4JgzL64lSzBiU5OgpgH0iF4KQDo_9vBQCOcFWYMiuUZLZqqmfpI5Y4orQIZQozOnppVGzGrfaQ1txRqJ3gXApoD2icAWpsuFf1VL9sgdqCyjLfIKPHDad_Nq3qCQ-b6PFmoQ8NnQoMn4S_tjCi6Cgy6p79TWdVJ-eOEyIw2XqFTU4D_khooo5FtbcOgwPs1es1e82tSDmUSzBEWmbWyXwPD5o0eMRrjqSM7LNPJBHu7s-gX1Omx4Fmr2pGGvPXg9t-UsG0R0ufwfcomflfSZ6-eJTFtlVmv3miygLV94wYh9PSzKKk7Rfa0qILT3pEZg5V5lkTQt07AowSD2XmITdMFEpVRpTNgcFHgJFZaYKeJ4FPg0MSEq0IbqGhUaXiuCqlltaQKNIqcTcrZ628anD7eiz4PpEqjiYArXm_7E4hJ1MrD4basHPS2U1AVJ6whmCz7izj0JYtI66Wpqoj7UJ7XB=s500-no'},
    {name: 'Peter Babcsany', gender: 'M', patronKindForHostel: 'Adult', picture: 'https://lh3.googleusercontent.com/8KNfVoakutrr6PlYWwRjJ53AYK42UBJgGezQG-I5lCyjovluq16B4qpqf0bI2hxxYycwPjgme6QxORuspS_IHRjoBbychGZBU93uB4bbVkjfgarXXdV9E1lDfcSyBYhTz9KXWQoHWTT_b3HyP0gtgszyEi1h4JgzL64lSzBiU5OgpgH0iF4KQDo_9vBQCOcFWYMiuUZLZqqmfpI5Y4orQIZQozOnppVGzGrfaQ1txRqJ3gXApoD2icAWpsuFf1VL9sgdqCyjLfIKPHDad_Nq3qCQ-b6PFmoQ8NnQoMn4S_tjCi6Cgy6p79TWdVJ-eOEyIw2XqFTU4D_khooo5FtbcOgwPs1es1e82tSDmUSzBEWmbWyXwPD5o0eMRrjqSM7LNPJBHu7s-gX1Omx4Fmr2pGGvPXg9t-UsG0R0ufwfcomflfSZ6-eJTFtlVmv3miygLV94wYh9PSzKKk7Rfa0qILT3pEZg5V5lkTQt07AowSD2XmITdMFEpVRpTNgcFHgJFZaYKeJ4FPg0MSEq0IbqGhUaXiuCqlltaQKNIqcTcrZ628anD7eiz4PpEqjiYArXm_7E4hJ1MrD4basHPS2U1AVJ6whmCz7izj0JYtI66Wpqoj7UJ7XB=s500-no'},
    {name: 'Peter Babcsany', gender: 'M', patronKindForHostel: 'Adult', picture: 'https://lh3.googleusercontent.com/8KNfVoakutrr6PlYWwRjJ53AYK42UBJgGezQG-I5lCyjovluq16B4qpqf0bI2hxxYycwPjgme6QxORuspS_IHRjoBbychGZBU93uB4bbVkjfgarXXdV9E1lDfcSyBYhTz9KXWQoHWTT_b3HyP0gtgszyEi1h4JgzL64lSzBiU5OgpgH0iF4KQDo_9vBQCOcFWYMiuUZLZqqmfpI5Y4orQIZQozOnppVGzGrfaQ1txRqJ3gXApoD2icAWpsuFf1VL9sgdqCyjLfIKPHDad_Nq3qCQ-b6PFmoQ8NnQoMn4S_tjCi6Cgy6p79TWdVJ-eOEyIw2XqFTU4D_khooo5FtbcOgwPs1es1e82tSDmUSzBEWmbWyXwPD5o0eMRrjqSM7LNPJBHu7s-gX1Omx4Fmr2pGGvPXg9t-UsG0R0ufwfcomflfSZ6-eJTFtlVmv3miygLV94wYh9PSzKKk7Rfa0qILT3pEZg5V5lkTQt07AowSD2XmITdMFEpVRpTNgcFHgJFZaYKeJ4FPg0MSEq0IbqGhUaXiuCqlltaQKNIqcTcrZ628anD7eiz4PpEqjiYArXm_7E4hJ1MrD4basHPS2U1AVJ6whmCz7izj0JYtI66Wpqoj7UJ7XB=s500-no'}
  ];

  private _datePickedFor: string;
  name: string;

  constructor(private formBuilder: FormBuilder, private toastCtrl: ToastController, private db: DataStorage) {
/*
    this.datePicker.onDateSelected.subscribe((date: Date) => {
      let input = this.templeTripGroup.controls[this._datePickedFor];
      if (input) {
        input.setValue(moment(date).format('YYYY-MM-DD'));
      }
    });
*/
    this.templeTripGroup = formBuilder.group({
      arrivalDate: ['', [Validators.required]],
      leaveDate: ['', Validators.required],
      patrons: [this.patrons, []]
    });
  }

  ionViewDidLoad() {
    console.log('SOMETHING from ionViewDidLoad')
    // this.db.fetchName().then((name: string) => this.name = name);
  }

  pickDate(datePickedFor: string) {
    this._datePickedFor = datePickedFor;
    // this[datePickedFor].click();
    // this.datePicker.showCalendar();
  }

  submitTrip() {
    let trip: {} = this.templeTripGroup.value;
    let persons: any[] = trip['patrons'];
    let personsText = persons.length > 1 ? `${persons.length} persons` : `one person`;
    let personsList: string = persons.map((person) => `* ${person.name} (${person.patronKindForHostel})`).join('\n');
    SocialSharing.canShareViaEmail().then(() => {
      SocialSharing.shareViaEmail(
        `Dear Hostel,\n\nI would like to reserve for ${personsText} from ${trip['arrivalDate']} to ${trip['leaveDate']}.\nHere are the names:\n\n${personsList}\n\nKind regards,\n${this.name}`,
        'Reservation from %1$s to %2$s for %3$s',
        ['peter@babcsany.com']//['freib-hos@ldschurch.org']
      ).then(() => {
        this.toastCtrl.create({
          message: 'Message Sent!',
          duration: 3000
        });
      })
    })
  }
}
