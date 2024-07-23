import {Book} from "./Book";
import {UserAvatar} from "../UserAvatar";

export interface ReservedBookDetails {
  id: number;
  dateFrom: Date,
  dateTo: Date,
  book: Book,
  customer: UserAvatar,
  store: string
}
