import { Component, ElementRef } from '@angular/core';
import { AdminMenuItemComponent } from '../admin-menu-item/admin-menu-item.component';
import { NgFor, NgIf } from '@angular/common';
import { AdminMenuComponent } from '../admin-menu/admin-menu.component';
import { LayoutService } from '../layout.service';

@Component({
  selector: 'app-admin-side-bar',
  standalone: true,
  imports: [AdminMenuComponent],
  templateUrl: './admin-side-bar.component.html',
  styleUrl: './admin-side-bar.component.css'
})
export class AdminSideBarComponent {
  constructor(public layoutService: LayoutService, public el: ElementRef) { }
}
