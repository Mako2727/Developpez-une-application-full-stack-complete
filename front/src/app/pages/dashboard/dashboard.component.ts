import { Component, OnInit } from '@angular/core';
import { SessionService } from '../../services/session.service';
import { User } from 'src/app/interfaces/user.interface';



@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  user: User | null | undefined = null;
  loading = true;

    constructor(private sessionService: SessionService) { }

  ngOnInit(): void {
     this.user = this.sessionService.user;
    this.loading = false;
  }

}
