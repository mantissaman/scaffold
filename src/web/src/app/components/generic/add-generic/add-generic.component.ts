import { Component } from '@angular/core';
import { Generic } from '../../../models/generic.model';
import { DataGatewayService } from '../../../_services/data-gateway.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-generic',
  templateUrl: './add-generic.component.html',
  styleUrls: ['./add-generic.component.css']
})
export class AddGenericComponent {

  generic: Generic = {
    generic_string: "",
    generic_int: 0,
    generic_numeric: 0,
    generic_bool: false,
    generic_tag: "",
    generic_text: ""
  };
  submitted = false;

  constructor(private dataService: DataGatewayService, private router: Router){}

  saveGeneric(): void {
    const data = {
      generic_string: this.generic.generic_string,
      generic_int: this.generic.generic_int,
      generic_numeric: this.generic.generic_numeric,
      generic_bool: this.generic.generic_bool,
      generic_tag: this.generic.generic_tag,
      generic_text: this.generic.generic_text
    };

    this.dataService.perform('upsert-generic-data', data)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.submitted = true;
        },
        error: (e) => console.error(e)
      });
  }

  newGeneric(): void {
    this.submitted = false;
    this.generic = {
      generic_string: "",
      generic_int: 0,
      generic_numeric: 0,
      generic_bool: false,
      generic_tag: "",
      generic_text: ""
    };
  }
  back(): void {
    this.router.navigate(['/generics']);
  }
}