import { Component } from '@angular/core';
import { PasswordModule } from 'primeng/password';
import { CheckboxModule } from 'primeng/checkbox';
import { LayoutService } from '../../admin-layout/layout.service';
import {Router, RouterLink} from '@angular/router';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import {InputTextModule} from "primeng/inputtext";
import {RippleModule} from "primeng/ripple";
import {log} from "@angular-devkit/build-angular/src/builders/ssr-dev-server";
import {UserService} from "../../service/user.service";
import {switchMap} from "rxjs";
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [PasswordModule, CheckboxModule, RouterLink, ToastModule, FormsModule, ButtonModule, InputTextModule, RippleModule, ReactiveFormsModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css',
  styles: [`
  :host ::ng-deep .pi-eye,
  :host ::ng-deep .pi-eye-slash {
      transform:scale(1.6);
      margin-right: 1rem;
      color: var(--primary-color) !important;
  }
`]
})
export class AuthComponent {

  valCheck: string[] = ['remember'];

  password!: string;
  loginForm = new FormGroup({
    email: new FormControl(''),
    password: new FormControl(''),
  })

  constructor(public layoutService: LayoutService,
              private userService: UserService,
              private router: Router,
              private messageService: MessageService,) { }

  onLogin() {
    this.userService.login(this.loginForm.value).subscribe(
      userResponse => {
        this.router.navigate(['/']); // Handle user response here
      },
      error => {
      this.messageService.add({severity: 'error', detail: 'An error occured while logging in. Please try again!'})
      }
    );
  }
}
