import {Component, OnInit} from '@angular/core';
import {PanelModule} from "primeng/panel";
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {DropdownModule} from "primeng/dropdown";
import {ButtonModule} from "primeng/button";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageService, SelectItem} from "primeng/api";
import {NgIf} from "@angular/common";
import {InputTextModule} from "primeng/inputtext";
import {InputTextareaModule} from "primeng/inputtextarea";
import {CheckboxModule} from "primeng/checkbox";
import {PasswordModule} from "primeng/password";
import {RippleModule} from "primeng/ripple";
import {ToastModule} from "primeng/toast";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    PanelModule,
    ReactiveFormsModule,
    DropdownModule,
    ButtonModule,
    NgIf,
    InputTextModule,
    InputTextareaModule,
    CheckboxModule,
    PasswordModule,
    RippleModule,
    ToastModule,
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {

  userForm: FormGroup;

  submitted: boolean | undefined;

  description: string | undefined;


  constructor(private fb: FormBuilder,
              private route: ActivatedRoute,
              private messageService: MessageService,
              private userService: UserService,
              private router: Router) {
    this.userForm = this.fb.group({
      'firstName': new FormControl('', Validators.required),
      'lastName': new FormControl('', Validators.required),
      'email': new FormControl('', Validators.required),
      'password': new FormControl('', Validators.compose([Validators.required, Validators.minLength(6)])),
    });
  }

  ngOnInit() {

  }

  onSubmit(value: string) {
    this.submitted = true;
    this.userService.register(this.userForm.value).subscribe(
      next => {
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Form Submitted'})
        this.router.navigate(['/login'])
      },
      error => this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'An error occurred while commiting the action!'
      })
    )
  }

  get diagnostic() {
    return JSON.stringify(this.userForm?.value);
  }
}
