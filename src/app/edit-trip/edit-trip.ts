import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from "@angular/forms";
import { TripData } from '../services/trip-data';
import { Trip } from '../models/trip';


@Component({
  selector: 'app-edit-trip',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './edit-trip.html',
  styleUrl: './edit-trip.css',
})

export class EditTrip implements OnInit {
  editForm!: FormGroup;
  trip!: Trip;
  submitted = false;
  message : string = '';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private tripData: TripData
  ) {}

  ngOnInit(): void {
    const code = this.route.snapshot.paramMap.get('code');
    //console.log('EditTrip::ngOnInit - code:', code);  

    if (!code) {
      alert("Something wrong, couldn't find where I stashed the code");
      this.router.navigate(['']);
      return;
    }

    console.log('EditTrip::ngOnInit');
    console.log('Code:', code);
    this.editForm = this.formBuilder.group({
      _id: [],
      code: [code, Validators.required],
      name: ['', Validators.required],
      start: ['', Validators.required],
      resort: ['', Validators.required],
      image: ['', Validators.required],
      length: ['', Validators.required],
      perPerson: ['', Validators.required],
      description: ['', Validators.required],
    })
    
    this.tripData.getTripByCode(code)
      .subscribe({
        next: (value: any) => {
          this.trip = value;
          // Update the form with the retrieved trip data
          this.editForm.patchValue(value);
          if(!value)
          {
            this.message = 'No trip retrieved';
          }
          else {
            this.message = 'Trip: ' + code + ' retrieved';
          }
          console.log(this.message);
        },
        error: (error: any) => {
          console.error('Error retrieving trip:', error);
          this.message = 'Error retrieving trip';
        }
      })
      
  }

  public onSubmit(): void {
    this.submitted = true;

    if (this.editForm.valid) {
      this.tripData.updateTrip(this.editForm.value)
      .subscribe({
        next: (value: any) => {
          console.log('Trip updated successfully:', value);
          this.router.navigate(['']);
        },
        error: (error: any) => {
          console.error('Error updating trip:', error);
        }
      });
    }
  }

  // get the form short name to access the form fields
get f() { return this.editForm.controls; }
}