import { Component } from '@angular/core';
import { SkeletonModule } from 'primeng/skeleton';

@Component({
  selector: 'book-loader',
  standalone: true,
  imports: [SkeletonModule],
  templateUrl: './book-loader.component.html',
  styleUrl: './book-loader.component.css'
})
export class BookLoaderComponent {

}
