import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Generic } from '../../../models/generic.model';
import { DataGatewayService } from '../../../_services/data-gateway.service';
@Component({
  selector: 'app-generic-details',
  templateUrl: './generic-details.component.html',
  styleUrls: ['./generic-details.component.css']
})
export class GenericDetailsComponent implements OnInit {

  @Input() viewMode = false;

  @Input() currentGeneric: Generic = {
    id: 0,
    generic_string: "",
    generic_int: 0,
    generic_numeric: 0,
    generic_bool: false,
    generic_tag: "",
    generic_text: ""
  };
  
  message = '';

  constructor(
    private dataService: DataGatewayService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    if (!this.viewMode) {
      this.message = '';
      this.getGeneric(this.route.snapshot.params["id"]);
    }
  }

  getGeneric(id: string): void {
    this.dataService.perform('get-generic-by-id', {id})
      .subscribe({
        next: (data) => {
          this.currentGeneric = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }



  updateGeneric(): void {
    this.message = '';

    this.dataService.perform('upsert-generic-data', this.currentGeneric)
    .subscribe({
      next: (res) => {
        console.log(res);
        this.message = res.message ? res.message : 'The status was updated successfully!';
      },
      error: (e) => console.error(e)
    });
  }

  deleteGeneric(): void {
    this.dataService.perform('delete-generic-data', {id: this.currentGeneric.id})
    .subscribe({
      next: (res) => {
        console.log(res);
        this.message = res.message ? res.message : 'The status was updated successfully!';
      },
      error: (e) => console.error(e)
    });
  }
  back(): void {
    this.router.navigate(['/generics']);
  }
}