import { Component, Input, OnInit } from '@angular/core';
import { SkeletonModule } from 'primeng/skeleton';
import { TableModule } from 'primeng/table';

@Component({
  selector: 'app-table-loader',
  standalone: true,
  imports: [SkeletonModule, TableModule],
  templateUrl: './table-loader.component.html',
  styleUrl: './table-loader.component.css'
})
export class TableLoaderComponent implements OnInit{
  @Input() fields: any[] | undefined;
  @Input() numberOfRows: number | undefined;
  values: any[] | undefined;
  
  ngOnInit(): void {
    this.values = Array.from({ length: this.numberOfRows ?? 0 }).map((_, i) => `Item #${i}`);
  }


}
