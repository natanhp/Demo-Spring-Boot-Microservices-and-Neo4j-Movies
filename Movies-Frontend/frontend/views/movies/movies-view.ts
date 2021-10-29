import '@polymer/iron-icon';
import { EndpointError } from '@vaadin/flow-frontend';
import { showNotification } from '@vaadin/flow-frontend/a-notification';
import { Binder, field } from '@vaadin/form';
import '@vaadin/vaadin-button';
import '@vaadin/vaadin-date-picker';
import '@vaadin/vaadin-date-time-picker';
import '@vaadin/vaadin-form-layout';
import '@vaadin/vaadin-grid';
import { GridDataProviderCallback, GridDataProviderParams, GridElement } from '@vaadin/vaadin-grid/vaadin-grid';
import '@vaadin/vaadin-grid/vaadin-grid-sort-column';
import '@vaadin/vaadin-icons';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-split-layout';
import '@vaadin/vaadin-template-renderer';
import '@vaadin/vaadin-text-field';
import '@vaadin/vaadin-upload';
import Movies from 'Frontend/generated/id/natanhp/data/entity/Movies';
import MoviesModel from 'Frontend/generated/id/natanhp/data/entity/MoviesModel';
import * as MoviesEndpoint from 'Frontend/generated/MoviesEndpoint';
import { html } from 'lit';
import { customElement, property, query, state } from 'lit/decorators.js';
import { View } from '../view';

@customElement('movies-view')
export class MoviesView extends View {
  @query('#grid')
  private grid!: GridElement;

  @property({ type: Number })
  private gridSize: number = 0;
  private selectedModel: Movies | null = null;

  private gridDataProvider = this.getGridData.bind(this);

  private binder = new Binder<Movies, MoviesModel>(this, MoviesModel);

  render() {
    return html`
      <vaadin-split-layout class="w-full h-full">
        <div class="flex-grow w-full">
          <vaadin-grid
            id="grid"
            class="w-full h-full"
            theme="no-border"
            .size=${this.gridSize}
            .dataProvider=${this.gridDataProvider}
            @active-item-changed=${this.itemSelected}
          >
            <vaadin-grid-sort-column auto-width path="title"></vaadin-grid-sort-column>
            <vaadin-grid-sort-column auto-width path="released"></vaadin-grid-sort-column>
          </vaadin-grid>
        </div>
        <div class="flex flex-col" style="width: 400px;">
          <div class="p-l flex-grow">
            <vaadin-form-layout
              ><vaadin-text-field label="Title" id="title" ${field(this.binder.model.title)}></vaadin-text-field
              ><vaadin-text-field
                label="Released"
                id="released"
                ${field(this.binder.model.released)}
              ></vaadin-text-field
            ></vaadin-form-layout>
          </div>
          <vaadin-horizontal-layout class="w-full flex-wrap bg-contrast-5 py-s px-l" theme="spacing">
            <vaadin-button theme="primary" @click=${this.save}>Create New</vaadin-button>
            <vaadin-button theme="primary" @click=${this.updateMovie}>Update</vaadin-button>
            <vaadin-button theme="primary" @click=${this.delete}>Delete</vaadin-button>
            <vaadin-button theme="tertiary" @click=${this.cancel}>Cancel</vaadin-button>
          </vaadin-horizontal-layout>
        </div>
      </vaadin-split-layout>
    `;
  }

  private async getGridData(
    params: GridDataProviderParams<Movies>,
    callback: GridDataProviderCallback<Movies | undefined>
  ) {
    const index = params.page * params.pageSize;
    const data = (await MoviesEndpoint.list(index, params.pageSize, params.sortOrders as any)) as Movies[];
    this.gridSize = data.length
    callback(data);
  }

  async connectedCallback() {
    super.connectedCallback();
    this.classList.add('flex', 'flex-col', 'h-full');
  }

  private async itemSelected(event: CustomEvent) {
    const item: Movies = event.detail.value as Movies;
    this.grid.selectedItems = item ? [item] : [];

    if (item) {
      this.selectedModel = item;
      const fromBackend = await MoviesEndpoint.get(item.id!);
      fromBackend ? this.binder.read(fromBackend) : this.refreshGrid();
    } else {
      this.selectedModel = null;
      this.clearForm();
    }
  }

  private async delete() {
    if (this.selectedModel?.id != null) {
      await MoviesEndpoint.delete(this.selectedModel.id)
      this.selectedModel = null
      this.clearForm();
      this.refreshGrid();
      showNotification(`Movies deleted.`, { position: 'bottom-start' });
    }
  }

  private async save() {
    try {
      const isNew = !this.binder.value.id;
      await this.binder.submitTo(MoviesEndpoint.create);
      if (isNew) {
        // We added a new item
        this.gridSize++;
      }
      this.clearForm();
      this.refreshGrid();
      showNotification(`Movies details stored.`, { position: 'bottom-start' });
    } catch (error: any) {
      if (error instanceof EndpointError) {
        showNotification(`Server error. ${error.message}`, { position: 'bottom-start' });
      } else {
        throw error;
      }
    }
  }

  private async updateMovie() {
    try {
      if (this.selectedModel?.id != null) {
        this.selectedModel.title = this.binder.value.title
        this.selectedModel.released = this.binder.value.released
        await MoviesEndpoint.update(this.selectedModel, this.selectedModel.id);
        this.clearForm();
        this.refreshGrid();
        showNotification(`Movies details stored.`, { position: 'bottom-start' });
      }
    } catch (error: any) {
      if (error instanceof EndpointError) {
        showNotification(`Server error. ${error.message}`, { position: 'bottom-start' });
      } else {
        throw error;
      }
    }
  }

  private cancel() {
    this.grid.activeItem = undefined;
    this.selectedModel = null;
  }

  private clearForm() {
    this.binder.clear();
  }

  private refreshGrid() {
    this.grid.selectedItems = [];
    this.grid.clearCache();
  }
}
