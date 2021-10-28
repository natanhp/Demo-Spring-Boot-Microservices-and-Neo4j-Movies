import { Route } from '@vaadin/router';
import './views/movies/movies-view';

export type ViewRoute = Route & {
  title?: string;
  icon?: string;
  children?: ViewRoute[];
};

export const views: ViewRoute[] = [
  // place routes below (more info https://vaadin.com/docs/latest/fusion/routing/overview)
  {
    path: '',
    component: 'movies-view',
    icon: '',
    title: '',
  },
  {
    path: 'movies',
    component: 'movies-view',
    icon: 'la la-columns',
    title: 'Movies',
  },
];
export const routes: ViewRoute[] = [...views];
