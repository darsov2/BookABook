import { Component } from '@angular/core';
import { LayoutService } from '../layout.service';
import { NgFor, NgIf } from '@angular/common';
import { AdminMenuItemComponent } from "../admin-menu-item/admin-menu-item.component";

@Component({
    selector: 'app-admin-menu',
    standalone: true,
    templateUrl: './admin-menu.component.html',
    styleUrl: './admin-menu.component.css',
    imports: [NgFor, NgIf, AdminMenuItemComponent]
})
export class AdminMenuComponent {
  model: any[] = [];

    constructor(public layoutService: LayoutService) { }

    ngOnInit() {
        this.model = [
            {
                label: 'Home',
                items: [
                    { label: 'Dashboard', icon: 'pi pi-fw pi-home', routerLink: ['/'] }
                ]
            },
            {
                label: 'Global Settings',
                items: [
                    { label: 'Books', icon: 'pi pi-fw pi-book', routerLink: ['/admin', 'books'] },
                    { label: 'Authors', icon: 'pi pi-fw pi-user', routerLink: ['/admin', 'authors']},
                    { label: 'Publishing Houses', icon: 'pi pi-fw pi-comment', routerLink: ['/admin', 'publishers'] },
                ]
            },
            {
                label: 'Library Management',
                items: [
                    { label: 'Locations', icon: 'pi pi-fw pi-map-marker', routerLink: ['/locations'] },
                    { label: 'Available Books', icon: 'pi pi-fw pi-warehouse', routerLink: ['/admin', 'copies'] },
                    { label: 'Lending', icon: 'pi pi-fw pi-folder-plus', routerLink: ['/admin', 'lendings'] },
                    { label: 'Customers', icon: 'pi pi-fw pi-id-card', url: ['https://www.primefaces.org/primeblocks-ng'], target: '_blank' },
                ]
            },
        ];
    }
}
