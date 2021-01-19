import { Icons } from 'src/app/enums/icons.enum';

export interface TableOperation {
    operation: () => void;
    icon: Icons;
}
