import {Component, OnInit} from '@angular/core';
import {CardModule} from "primeng/card";
import {ProgressBarModule} from "primeng/progressbar";
import {StatsService} from "../service/stats.service";
import {DecimalPipe} from "@angular/common";

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    CardModule,
    ProgressBarModule,
    DecimalPipe
  ],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit {

  constructor(private statsService: StatsService) {
  }

  inventoryPercentage: number = 0

  ngOnInit(): void {
    this.statsService.getInventoryForStore().subscribe(x => {
      console.log(x);
      this.inventoryPercentage = x
    })
  }


}
