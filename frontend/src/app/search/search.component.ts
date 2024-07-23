import { Component } from '@angular/core';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { RouterLink, RouterModule, RouterOutlet } from '@angular/router';

@Component({
  selector: 'search',
  standalone: true,
  imports: [InputTextModule, DropdownModule, RouterLink, RouterOutlet, RouterModule],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})

export class SearchComponent {
  alphabet: String[] = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '#', 'ALL'];
  categories: String[] = ['Horror', 'Drama', 'Action'];

}
