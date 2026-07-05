import { Component, OnInit, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Trip } from '../models/trip';
import { Router } from '@angular/router';
import { Authentication } from '../services/authentication';

@Component({
  selector: 'app-trip-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './trip-card.html',
  styleUrl: './trip-card.css'
  
})
export class TripCard implements OnInit {
  @Input() trip!: Trip;

  constructor(private router: Router, private authService: Authentication) {}

  ngOnInit(): void {
    console.log('TripCard::ngOnInit', this.trip);
  }

  public editTrip(trip: Trip): void {
   // localStorage.removeItem('code');
   // localStorage.setItem('code', trip.code);
    this.router.navigate(['/edit-trip', trip.code]);
  }

  public isLoggedIn(){
    return this.authService.isLoggedIn();
  }
}
