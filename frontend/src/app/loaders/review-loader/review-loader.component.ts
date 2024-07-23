import { Component } from '@angular/core';
import { SkeletonModule } from 'primeng/skeleton';

@Component({
  selector: 'review-loader',
  standalone: true,
  imports: [SkeletonModule],
  templateUrl: './review-loader.component.html',
  styleUrl: './review-loader.component.css'
})
export class ReviewLoaderComponent {

}
