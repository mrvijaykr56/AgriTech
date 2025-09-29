import { Component, OnInit } from '@angular/core';
import { Farmer } from '../../models/farmer.module';
import { FarmerService } from '../../services/farmer.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-farmer',
  imports:[FormsModule,CommonModule],
  templateUrl: './farmer.component.html',
  styleUrls: ['./farmer.component.css']
})
export class FarmerComponent implements OnInit {
  farmers: Farmer[] = [];
  selectedFarmer?: Farmer;

  // Form models
  newFarmer: Partial<Farmer> = { name: '', mobile: '', village: '', consentGiven: false };
  searchVillage = '';

  // UI state
  loading = false;
  error = '';
  success = '';

  constructor(private farmerService: FarmerService) {}

  ngOnInit(): void {
    this.loadFarmers();
  }

  // Load all farmers
  loadFarmers(): void {
    this.setLoading(true);
    this.farmerService.getAll().subscribe({
      next: (data) => {
        this.farmers = data;
        this.success = 'Farmers loaded';
        this.setLoading(false);
      },
      error: (err) => this.handleError(err, 'Failed to load farmers')
    });
  }

  // Reset search and reload
  reset(): void {
    this.searchVillage = '';
    this.loadFarmers();
  }

  // Search by village
  search(): void {
    this.setLoading(true);
    this.farmerService.search(this.searchVillage).subscribe({
      next: (data) => {
        this.farmers = data;
        this.success = 'Search completed';
        this.setLoading(false);
      },
      error: (err) => this.handleError(err, 'Search failed')
    });
  }

  // Create
  addFarmer(): void {
    this.setLoading(true);
    this.farmerService.create(this.newFarmer).subscribe({
      next: (created) => {
        this.farmers.unshift(created);
        this.newFarmer = { name: '', mobile: '', village: '', consentGiven: false };
        this.success = 'Farmer created';
        this.setLoading(false);
      },
      error: (err) => this.handleError(err, 'Failed to create farmer')
    });
  }

  // Select for edit
  selectFarmer(farmer: Farmer): void {
    this.selectedFarmer = { ...farmer };
    this.error = '';
    this.success = '';
  }

  // Update
  updateFarmer(): void {
    if (!this.selectedFarmer?.farmerId) return;
    this.setLoading(true);
    this.farmerService.update(this.selectedFarmer.farmerId, this.selectedFarmer).subscribe({
      next: (updated) => {
        const idx = this.farmers.findIndex((f) => f.farmerId === updated.farmerId);
        if (idx > -1) this.farmers[idx] = updated;
        this.selectedFarmer = undefined;
        this.success = 'Farmer updated';
        this.setLoading(false);
      },
      error: (err) => this.handleError(err, 'Failed to update farmer')
    });
  }

  // Delete
  deleteFarmer(id: number): void {
    if (!confirm('Delete this farmer? This may remove related logs if cascade is enabled.')) return;
    this.setLoading(true);
    this.farmerService.delete(id).subscribe({
      next: () => {
        this.farmers = this.farmers.filter((f) => f.farmerId !== id);
        if (this.selectedFarmer?.farmerId === id) this.selectedFarmer = undefined;
        this.success = 'Farmer deleted';
        this.setLoading(false);
      },
      error: (err) => this.handleError(err, 'Failed to delete farmer')
    });
  }

  // Cancel edit
  cancelEdit(): void {
    this.selectedFarmer = undefined;
  }

  // Helpers
  private setLoading(val: boolean): void {
    this.loading = val;
    if (val) this.error = '';
  }

  private handleError(err: unknown, fallbackMsg: string): void {
    console.error(err);
    this.error = fallbackMsg;
    this.loading = false;
  }
}