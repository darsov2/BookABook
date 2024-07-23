import { Component, Input, input } from '@angular/core';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { BookCard } from '../Book-Card';


@Component({
  selector: 'book-card',
  standalone: true,
  imports: [CardModule, ButtonModule],
  templateUrl: './book-card.component.html',
  styleUrl: './book-card.component.css'
})
export class BookCardComponent {

@Input() book: BookCard | undefined

}
