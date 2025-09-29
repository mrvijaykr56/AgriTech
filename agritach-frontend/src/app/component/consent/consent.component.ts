import { Component } from '@angular/core';
import { ConsentDetails, ConsentService } from '../../services/consentlog.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-consent',
  imports:[FormsModule,CommonModule],
  templateUrl: './consent.component.html',
  styleUrls: ['./consent.component.css']
})
export class ConsentComponent {
  farmerId: number | null = null;
  details: ConsentDetails | null = null;
  note = '';
  loading = false;
  error = '';
  success = '';

  constructor(private service: ConsentService) {}

  fetch(): void {
    if (!this.farmerId) return;
    this.loading = true;
    this.service.getConsentDetails(this.farmerId).subscribe({
      next: data => {
        this.details = data;
        this.loading = false;
        this.error = '';
      },
      error: err => {
        this.error = 'Farmer not found';
        this.details = null;
        this.loading = false;
      }
    });
  }

  record(value: boolean): void {
  if (!this.farmerId) return;

  this.loading = true;
  this.error = '';
  this.success = '';

  this.service.recordConsent(this.farmerId, value, this.note).subscribe({
    next: (msg: string) => {
      this.success = msg;
      this.note = '';
      this.fetch(); // refresh consent details
    },
    error: err => {
      this.error = 'Failed to record consent';
      this.loading = false;
    }
  });
}
}