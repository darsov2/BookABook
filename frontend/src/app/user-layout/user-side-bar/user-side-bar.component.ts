import { Component, ElementRef } from '@angular/core';
import { UserMenuItemComponent } from '../user-menu-item/user-menu-item.component';
import { NgFor, NgIf } from '@angular/common';
import { UserMenuComponent } from '../user-menu/user-menu.component';
import { LayoutService } from '../../admin-layout/layout.service';

@Component({
  selector: 'user-side-bar',
  standalone: true,
  imports: [UserMenuComponent],
  templateUrl: './user-side-bar.component.html',
  styleUrl: './user-side-bar.component.css'
})
export class UserSideBarComponent {
  constructor(public layoutService: LayoutService, public el: ElementRef) { }
}
