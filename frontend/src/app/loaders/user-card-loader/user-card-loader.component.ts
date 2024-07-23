import { Component } from '@angular/core';
import {SkeletonModule} from "primeng/skeleton";

@Component({
  selector: 'app-user-card-loader',
  standalone: true,
  imports: [
    SkeletonModule
  ],
  templateUrl: './user-card-loader.component.html',
  styleUrl: './user-card-loader.component.css'
})
export class UserCardLoaderComponent {

}
