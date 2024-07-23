import {Component, Input, OnInit} from '@angular/core';
import {InputTextModule} from "primeng/inputtext";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {CarouselBookCardComponent} from "../carousel-book-card/carousel-book-card.component";
import {KeyValuePipe, NgIf} from "@angular/common";
import {SearchComponent} from "../search/search.component";
import {BookCard} from "../Book-Card";
import {BookService} from "../service/book.service";
import {Observable} from "rxjs";
import {Book} from "../Book";
import {LendingBookDetailsComponent} from "./lending-book-details/lending-book-details.component";
import {StepperModule} from "primeng/stepper";
import {ButtonModule} from "primeng/button";
import {BookLendingCopy} from "../../BookLendingCopy";
import {BookLendingDetails} from "../BookLendingDetails";
import {FormsModule} from "@angular/forms";
import {UserCardLoaderComponent} from "../loaders/user-card-loader/user-card-loader.component";
import {UserService} from "../service/user.service";
import {UserAvatar} from "../../UserAvatar";
import {AvatarModule} from "primeng/avatar";
import {PanelModule} from "primeng/panel";
import {BookDetailsComponent} from "../book-details/book-details.component";
import {ImageModule} from "primeng/image";
import {MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";

@Component({
  selector: 'app-lending',
  standalone: true,
  imports: [
    InputTextModule,
    RouterLink,
    CarouselBookCardComponent,
    KeyValuePipe,
    SearchComponent,
    LendingBookDetailsComponent,
    StepperModule,
    ButtonModule,
    NgIf,
    FormsModule,
    UserCardLoaderComponent,
    AvatarModule,
    PanelModule,
    ImageModule,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './lending.component.html',
  styleUrl: './lending.component.css'
})
export class LendingComponent implements OnInit {
  alphabet: String[] = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '#', 'ALL'];
  selectedLetter: string = '';
  loading = true;
  topBookByLetters: Map<String, BookLendingDetails[]> | undefined;
  bookToLend: number | undefined;
  userSearch: string = "";
  userToDisplay: UserAvatar | undefined;
  userLoading: boolean = true;
  book: BookLendingDetails | undefined;

  chosenBook(id: number) {
    this.bookToLend = id
    this.book = Object.values(this.topBookByLetters || [])
      .flatMap(x => x as BookLendingDetails[])
      .find(x => (x as BookLendingDetails).bookCopies.some(copy => copy.id == this.bookToLend))
    console.log(this.book)
    console.log(this.bookToLend)
    this.userToDisplay = undefined;
    this.userLoading = true;
  }


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookService: BookService,
    private userService: UserService,
    private messageService: MessageService
  ) {
  }

  fetchData(letter: string | undefined = undefined) {
    this.messageService.add({severity: 'success', summary: 'Success', detail: 'Book successfully lent!'})
    this.bookService.getBooksByLetter(letter)
      .subscribe(
        res => {
          this.topBookByLetters = res
          this.loading = false
        }
      )
  }

  findUser($event: SubmitEvent) {
    console.log($event)
    console.log(this.userSearch)
    this.userLoading = true;
    this.userService.getCustomerShortInfoById(+this.userSearch).subscribe(x => {
      this.userToDisplay = x
      this.userLoading = false;
    })
  }


  ngOnInit(): void {
    this.fetchData()
  }

  filterByLetter(letter: string) {
    this.selectedLetter = letter;
    this.fetchData(this.selectedLetter)
  }

  confirmLending() {
    const userId = this.userToDisplay?.id;
    const copyId = this.bookToLend;
    if (userId == undefined || copyId == undefined) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'You have to select all the information to lend a book!'
      })
      return;
    }
    this.bookService.lendBook(userId, copyId).subscribe({
        next: _ =>
        {
          this.messageService.add({severity: 'success', summary: 'Success', detail: 'Book successfully lent!'})
          this.router.navigate(['/admin', 'lendings']);
        },

        error: _ => this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'An error occurred while saving the information!'
        })
      }
    )
  }
}
