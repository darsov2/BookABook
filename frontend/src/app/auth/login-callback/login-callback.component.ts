import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, Routes} from "@angular/router";
import {ProgressSpinnerModule} from "primeng/progressspinner";

@Component({
  selector: 'app-login-callback',
  standalone: true,
  imports: [
    ProgressSpinnerModule
  ],
  templateUrl: './login-callback.component.html',
  styleUrl: './login-callback.component.css'
})
export class LoginCallbackComponent implements OnInit{
  constructor(private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
        this.route.queryParamMap.subscribe(x => {
          const token = x.get('token')
          console.log(x)
          if(token != undefined){
            localStorage.setItem('token', token)
            this.router.navigate(['/'])
          }
        })
    }
}
