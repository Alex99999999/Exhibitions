<form class="d-flex">
    <form>
        <select class="form-select form-select-md " name="language" onchange="submit()">
          <option value="en" ${language == 'en' ? 'selected' : ''}>EN</option>
          <option value="uk" ${language == 'uk' ? 'selected' : ''}>UA</option>
      </select>
    </form>
</form>