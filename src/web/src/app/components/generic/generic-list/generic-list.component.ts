import { Component, OnInit } from '@angular/core';
import { Generic } from '../../../models/generic.model';
import { DataGatewayService } from '../../../_services/data-gateway.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-generic-list',
  templateUrl: './generic-list.component.html',
  styleUrls: ['./generic-list.component.css']
})
export class GenericListComponent implements OnInit {

  generics?: Generic[];
  currentGeneric: Generic = {};
  currentIndex = -1;
  generic_string = '';

  constructor(private dataService: DataGatewayService, private router: Router){}

  ngOnInit(): void {
    this.retrieveGenerics();
  }

  retrieveGenerics(): void {
    this.dataService.perform('get-generic-data', {})
      .subscribe({
        next: (data) => {
          this.generics = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });

  }

  refreshList(): void {
    this.retrieveGenerics();
    this.currentGeneric = {};
    this.currentIndex = -1;
  }

  setActiveGeneric(generic: Generic, index: number): void {
    this.currentGeneric = generic;
    this.currentIndex = index;
  }
  back(): void {
    this.router.navigate(['/home']);
  }
  newGeneric(): void {
    this.router.navigate(['/add-generic']);
  }
}