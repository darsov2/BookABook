import { Component, ElementRef, ViewChild } from '@angular/core';
import { MenuItem } from 'primeng/api/menuitem';
import { LayoutService } from '../layout.service';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-admin-top-bar',
  standalone: true,
  imports: [NgClass],
  templateUrl: './admin-top-bar.component.html',
  styleUrl: './admin-top-bar.component.css'
})
export class AdminTopBarComponent {
  items!: MenuItem[];

  @ViewChild('menubutton') menuButton!: ElementRef;

  @ViewChild('topbarmenubutton') topbarMenuButton!: ElementRef;

  @ViewChild('topbarmenu') menu!: ElementRef;

  constructor(public layoutService: LayoutService) { }
}
