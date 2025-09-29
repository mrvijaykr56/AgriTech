export interface FundingMilestone {
  id?: number;
  code: string;
  title: string;
  description: string;
  dueDate: number;
  achieved: boolean;
  achievedAt?: number;
  evidenceUrl?: string;
}