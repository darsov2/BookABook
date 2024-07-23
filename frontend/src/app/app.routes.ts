import {Routes} from '@angular/router';
import {AdminLayoutComponent} from './admin-layout/admin-layout.component';
import {AuthComponent} from './auth/login/auth.component';
import {BookService} from './service/book.service';
import {BooksListingComponent} from './books-listing/books-listing.component';
import {BookCardComponent} from './book-card/book-card.component';
import {UserLayoutComponent} from './user-layout/user-layout.component';
import {SearchComponent} from './search/search.component';
import {ListBooksComponent} from './list-books/list-books.component';
import {BookDetailsComponent} from './book-details/book-details.component';
import {AuthorsListingComponent} from "./authors-listing/authors-listing.component";
import {LendingComponent} from "./lending/lending.component";
import {LendingsListingComponent} from "./lendings-listing/lendings-listing.component";
import {UserReservationsComponent} from "./user-reservations/user-reservations.component";
import {UserWishlistComponent} from "./user-wishlist/user-wishlist.component";
import {UserDashboardComponent} from "./user-dashboard/user-dashboard.component";
import {RegisterComponent} from "./auth/register/register.component";
import {LoginCallbackComponent} from "./auth/login-callback/login-callback.component";
import {CopiesListingComponent} from "./copies-listing/copies-listing.component";
import {ReservationsListingComponent} from "./reservations-listing/reservations-listing.component";
import {AdminDashboardComponent} from "./admin-dashboard/admin-dashboard.component";

export const routes: Routes = [
  {
    path: 'login',
    component: AuthComponent,
  },
  {
    path: 'login-callback',
    component: LoginCallbackComponent,
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'admin',
    component: AdminLayoutComponent,
    children: [
      {
        path: 'books',
        component: BooksListingComponent
      },
      {
        path: 'authors',
        component: AuthorsListingComponent
      },
      {
        path: 'lending',
        component: LendingComponent
      },
      {
        path: 'lendings',
        component: LendingsListingComponent
      },
      {
        path: 'copies',
        component: CopiesListingComponent
      },
      {
        path: 'reservations',
        component: ReservationsListingComponent
      },
      {
        path: '',
        component: AdminDashboardComponent
      }
    ]
  },
  {
    path: '',
    component: UserLayoutComponent,
    children: [
      {
        path: 'books',
        component: ListBooksComponent,
        data: { listType: 'user'},
        resolve: {
          listType: () => 'user'
        }
      },
      {
        path: 'books/details/:id',
        component: BookDetailsComponent
      },
      {
        path: 'reservations',
        component: UserReservationsComponent,
      },
      {
        path: 'wishlist',
        component: UserWishlistComponent,
      },
      {
        path: '',
        component: UserDashboardComponent
      }
    ]
  },
  {
    path: 'search',
    component: SearchComponent
  },
  {
    path: 'book/details',
    component: BookDetailsComponent
  }
];
