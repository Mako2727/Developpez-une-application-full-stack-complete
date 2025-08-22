import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // Nécessaire pour *ngIf, *ngFor, pipes...

@Component({
  selector: 'app-articledetails',
  standalone: true, // 👈 rend le composant standalone
  imports: [CommonModule], // 👈 tu ajoutes ici les modules Angular dont tu as besoin
  templateUrl: './articledetails.component.html',
  styleUrls: ['./articledetails.component.scss']
})
export class ArticledetailsComponent {
  // plus besoin de implements OnInit si tu n’utilises pas ngOnInit
  // mais tu peux le garder si tu veux exécuter du code à l’init

  // Exemple si tu gardes ngOnInit
  ngOnInit(): void {
    console.log('ArticledetailsComponent chargé');
  }
}