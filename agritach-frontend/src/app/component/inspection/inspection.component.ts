import { Component, OnInit } from '@angular/core';   // ✅ ensure model file is consistent
import { InspectionService } from '../../services/inspection.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Inspection } from '../../models/inspection.module';

@Component({
  selector: 'app-inspection',
  standalone: true,                        // ✅ mark as standalone
  imports: [FormsModule, CommonModule],
  templateUrl: './inspection.component.html',
  styleUrls: ['./inspection.component.css']
})
export class InspectionComponent implements OnInit {
  inspections: Inspection[] = [];
  fieldIdFilter = '';
  newInspection: Partial<Inspection> = {};
  error = '';
  success = '';
  loading = false;                          // ✅ track loading state

  constructor(private inspectionService: InspectionService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  private setLoading(val: boolean): void {
    this.loading = val;
    if (val) {
      this.error = '';
      this.success = '';
    }
  }

  loadAll(): void {
    this.setLoading(true);
    this.inspectionService.getAll().subscribe({
      next: (data) => {
        this.inspections = data;
        this.success = 'Loaded all inspections';
        this.setLoading(false);
      },
      error: () => {
        this.error = 'Failed to load inspections';
        this.setLoading(false);
      }
    });
  }

  loadByField(): void {
    if (!this.fieldIdFilter) {
      this.loadAll();
      return;
    }
    this.setLoading(true);
    this.inspectionService.getByField(this.fieldIdFilter).subscribe({
      next: (data) => {
        this.inspections = data;
        this.success = `Loaded inspections for field ${this.fieldIdFilter}`;
        this.setLoading(false);
      },
      error: () => {
        this.error = 'Failed to load inspections by field';
        this.setLoading(false);
      }
    });
  }

  createInspection(): void {
    if (!this.newInspection.fieldId) return;
    this.setLoading(true);
    this.inspectionService.create(this.newInspection).subscribe({
      next: (created) => {
        this.inspections.unshift(created);
        this.newInspection = {};
        this.success = 'Inspection created';
        this.setLoading(false);
      },
      error: () => {
        this.error = 'Failed to create inspection';
        this.setLoading(false);
      }
    });
  }

deleteInspection(id: number): void {
  if (!confirm('Delete this inspection?')) return;
  this.inspectionService.delete(id).subscribe({
    next: () => {
      this.inspections = this.inspections.filter(i => i.inspectionId !== id);
      this.success = 'Inspection deleted';
    },
    error: () => (this.error = 'Failed to delete inspection')
  });
}
}