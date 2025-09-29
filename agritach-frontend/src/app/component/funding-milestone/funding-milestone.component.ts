import { Component, OnInit } from '@angular/core';
import { FundingMilestone } from '../../models/funding-milestone.module';
import { MilestoneService } from '../../services/funding.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-milestone',
  imports:[FormsModule,CommonModule],
  templateUrl: './funding-milestone.component.html',
  styleUrls: ['./funding-milestone.component.css']
})
export class MilestoneComponent implements OnInit {
  milestones: FundingMilestone[] = [];
  selected: FundingMilestone = {
    code: '',
    title: '',
    description: '',
    dueDate: Date.now(),
    achieved: false
  };

  loading = false;
  error = '';
  success = '';

  constructor(private service: MilestoneService) {}

  ngOnInit(): void {
    this.loadMilestones();
  }

  loadMilestones(): void {
    this.loading = true;
    this.service.getAll().subscribe({
      next: data => {
        this.milestones = data;
        this.loading = false;
      },
      error: err => {
        this.error = 'Failed to load milestones';
        this.loading = false;
      }
    });
  }

  save(): void {
    this.loading = true;
    const milestone = { ...this.selected };

    if (milestone.achieved && !milestone.achievedAt) {
      milestone.achievedAt = Date.now();
    } else if (!milestone.achieved) {
      milestone.achievedAt = 0;
    }

    const request = milestone.id
      ? this.service.update(milestone.id, milestone)
      : this.service.create(milestone);

    request.subscribe({
      next: () => {
        this.success = milestone.id ? 'Milestone updated successfully' : 'Milestone created successfully';
        this.loadMilestones();
        this.resetForm();
      },
      error: err => {
        this.error = 'Failed to save milestone';
        this.loading = false;
      }
    });
  }

  edit(m: FundingMilestone): void {
    this.selected = { ...m };
    this.success = '';
    this.error = '';
  }

  delete(id: number): void {
    if (!confirm('Are you sure you want to delete this milestone?')) return;

    this.loading = true;
    this.service.delete(id).subscribe({
      next: () => {
        this.success = 'Milestone deleted successfully';
        this.loadMilestones();
      },
      error: err => {
        this.error = 'Failed to delete milestone';
        this.loading = false;
      }
    });
  }

  resetForm(): void {
    this.selected = {
      code: '',
      title: '',
      description: '',
      dueDate: Date.now(),
      achieved: false
    };
    this.success = '';
    this.error = '';
  }
}