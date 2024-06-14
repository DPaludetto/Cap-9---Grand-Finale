
export interface CrudMode {
  type: string;
  name: string;
}

export const CRUD_LIST: CrudMode[] = [
  { type: 'EDIT', name: 'Editar' },
  { type: 'CREATE', name: 'Novo' },
  { type: 'DETAIL', name: 'Detalhe' },
];
